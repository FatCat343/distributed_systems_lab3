package ru.distributed_systems.lab3.service;

import generated.Node;
import org.springframework.stereotype.Service;
import ru.distributed_systems.lab3.model.Converter;
import ru.distributed_systems.lab3.model.entity.NodeEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class OsmService {
    private final NodeService nodeService;

    public OsmService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void handleOsm() throws IOException, XMLStreamException, JAXBException {

        try (
                InputStream fileInputStream = Files.newInputStream(Paths.get("RU-NVS.osm"));
                StaxStreamHandler handler = new StaxStreamHandler(fileInputStream)
        ) {
            processDataFrom(handler);
        }
    }

    private void processDataFrom(StaxStreamHandler handler) throws JAXBException, XMLStreamException {
        XMLStreamReader reader = handler.getReader();
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        int count = 0;
        while (reader.hasNext()) {
            int event = reader.next();
            if (isNodeSectionStart(reader, event)) {
                Node node = (Node) unmarshaller.unmarshal(reader);
                if (count % 1000 == 0 && count != 0) {
                    System.out.println("Current input objects: " + count);
                    System.out.println("-----------------------------------");
                }
                NodeEntity entity = Converter.convertNodeToNodeEntity(node);
                nodeService.saveNodeEntityBatched(entity);
                count++;
                if (count == 50_000)
                    break;
            }
        }
    }

    private boolean isNodeSectionStart(XMLStreamReader reader, int event) {
        return event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName());
    }
}


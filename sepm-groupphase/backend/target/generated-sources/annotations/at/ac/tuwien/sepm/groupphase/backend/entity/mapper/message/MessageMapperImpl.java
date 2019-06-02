package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.message;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDTO;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.DetailedMessageDTO.MessageDTOBuilder;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message.MessageBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-02T22:09:01+0200",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Autowired
    private MessageSummaryMapper messageSummaryMapper;

    @Override
    public Message detailedMessageDTOToMessage(DetailedMessageDTO detailedMessageDTO) {
        if ( detailedMessageDTO == null ) {
            return null;
        }

        MessageBuilder message = Message.builder();

        message.id( detailedMessageDTO.getId() );
        message.publishedAt( detailedMessageDTO.getPublishedAt() );
        message.title( detailedMessageDTO.getTitle() );
        message.text( detailedMessageDTO.getText() );

        return message.build();
    }

    @Override
    public DetailedMessageDTO messageToDetailedMessageDTO(Message one) {
        if ( one == null ) {
            return null;
        }

        MessageDTOBuilder detailedMessageDTO = DetailedMessageDTO.builder();

        detailedMessageDTO.id( one.getId() );
        detailedMessageDTO.publishedAt( one.getPublishedAt() );
        detailedMessageDTO.title( one.getTitle() );
        detailedMessageDTO.text( one.getText() );

        return detailedMessageDTO.build();
    }

    @Override
    public List<SimpleMessageDTO> messageToSimpleMessageDTO(List<Message> all) {
        if ( all == null ) {
            return null;
        }

        List<SimpleMessageDTO> list = new ArrayList<SimpleMessageDTO>( all.size() );
        for ( Message message : all ) {
            list.add( messageToSimpleMessageDTO( message ) );
        }

        return list;
    }

    @Override
    public SimpleMessageDTO messageToSimpleMessageDTO(Message one) {
        if ( one == null ) {
            return null;
        }

        at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.message.SimpleMessageDTO.MessageDTOBuilder simpleMessageDTO = SimpleMessageDTO.builder();

        simpleMessageDTO.summary( messageSummaryMapper.trimTextToSummary( one.getText() ) );
        simpleMessageDTO.id( one.getId() );
        simpleMessageDTO.publishedAt( one.getPublishedAt() );
        simpleMessageDTO.title( one.getTitle() );

        return simpleMessageDTO.build();
    }
}

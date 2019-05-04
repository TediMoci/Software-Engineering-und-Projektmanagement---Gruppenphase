package at.ac.tuwien.sepm.groupphase.backend.service.implementation.Actors;

import at.ac.tuwien.sepm.groupphase.backend.repository.Actors.IDudeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.Actors.IDudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DudeService implements IDudeService {

    private final IDudeRepository iDudeRepository;

    public DudeService(IDudeRepository iDudeRepository) {
        this.iDudeRepository = iDudeRepository;
    }
}

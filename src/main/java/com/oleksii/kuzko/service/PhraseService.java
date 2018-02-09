package com.oleksii.kuzko.service;

import com.oleksii.kuzko.dao.PhraseDao;
import com.oleksii.kuzko.model.Phrase;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author The Weather Company, An IBM Business
 */
@Service
public class PhraseService {

    private final PhraseDao phraseDao;

    public PhraseService(PhraseDao phraseDao) {
        this.phraseDao = phraseDao;
    }

    public Phrase getRandom() {
        return null;
    }

    public List<Phrase> getAll() {
        return phraseDao.getAll();
    }
}

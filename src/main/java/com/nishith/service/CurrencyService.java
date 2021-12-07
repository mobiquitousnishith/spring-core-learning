package com.nishith.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.models.Currency;
import com.nishith.repository.CurrencyRepository;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Optional<Currency> getCurrencyByName(String currencyName) {
        return currencyRepository.findByName(currencyName);
    }

    public Currency addCurrency(Currency currency) {
        return currencyRepository.addCurrency(currency);
    }
}

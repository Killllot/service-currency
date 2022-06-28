package org.example.cbrrate.parser;

import org.example.cbrrate.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}

package com.evatool.analysis.domain.events.json;

import com.evatool.analysis.domain.model.Value;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueJsonMapper {

    private ValueJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ValueJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static String toJson(Value value) {
        logger.info("Mapping Entity to Json");
        var valueJson = modelMapper.map(value, ValueJson.class);
        if (value.getAnalysis() != null) {
            valueJson.setAnalysisId(value.getAnalysis().getAnalysisId().toString());
        }
        return new Gson().toJson(valueJson);
    }
}

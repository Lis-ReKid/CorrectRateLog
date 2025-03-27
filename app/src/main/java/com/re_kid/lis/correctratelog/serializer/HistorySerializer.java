package com.re_kid.lis.correctratelog.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.re_kid.lis.correctratelog.obj.History;

import java.io.IOException;

public class HistorySerializer extends StdSerializer<History> {
    public HistorySerializer() {
        this(null);
    }

    public HistorySerializer(Class<History> historyClass) {
        super(historyClass);
    }

    @Override
    public void serialize(History value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("_id", value.getId());
        gen.writeNumberField("category_id", value.getCategory().getId());
        gen.writeStringField("learned_date", value.getLearnedDate().toString());
        gen.writeStringField("learned_time", value.getLearnedTime().toString());
        gen.writeNumberField("correct_number", value.getCorrectNum());
        gen.writeNumberField("entire_number", value.getEntireNum());
        gen.writeNumberField("correct_rate", value.getCorrectRate().getCorrectRate());
    }
}

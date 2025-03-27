package com.re_kid.lis.correctratelog.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.re_kid.lis.correctratelog.obj.History;
import com.re_kid.lis.correctratelog.obj.Category;
import com.re_kid.lis.correctratelog.obj.CorrectRate;
import com.re_kid.lis.correctratelog.obj.LearnedDate;
import com.re_kid.lis.correctratelog.obj.LearnedTime;

import java.io.IOException;

public class HistoryDeserializer extends StdDeserializer<History> {

    public HistoryDeserializer() {
        this(null);
    }

    public HistoryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public History deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        int id = node.get("_id").asInt();
        int categoryId = node.get("category_id").asInt();
        String learnedDateStr = node.get("learned_date").asText();
        String learnedTimeStr = node.get("learned_time").asText();
        int correctNum = node.get("correct_number").asInt();
        int entireNum = node.get("entire_number").asInt();
        double correctRateValue = node.get("correct_rate").asDouble();

        // 必要なら対応するコンストラクタを使ってインスタンス化
        Category category = new Category(categoryId, "tempName");
        LearnedDate learnedDate = LearnedDate.parse(learnedDateStr);
        LearnedTime learnedTime = LearnedTime.parse(learnedTimeStr);
        CorrectRate correctRate = new CorrectRate(correctRateValue);

        // History のコンストラクタに応じて修正する
        return new History(id, category, learnedDate, learnedTime, correctNum, entireNum, correctRate);
    }
}
package com.example.anusha.LiWire.data;

import org.json.JSONObject;

public interface JSONPopulator {

    void populate(JSONObject data);
    JSONObject toJSON();
}

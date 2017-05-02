package com.example.anusha.LiWire.data;

import org.json.JSONObject;

/**
 * Created by anusha on 12/5/16.
 */
public interface JSONPopulator {

    void populate(JSONObject data);
    JSONObject toJSON();
}

package com.ambientdigitalgroup.ambchat.networks;

import java.util.Map;

import okhttp3.Request;

public class AddFriendRequest extends  SeverRequest {
    public AddFriendRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {
        return null;
    }

    @Override
    protected Object process(String data) {
        return null;
    }
}

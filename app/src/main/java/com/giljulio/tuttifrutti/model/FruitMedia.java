package com.giljulio.tuttifrutti.model;

/**
 * Created by Gil on 08/08/15.
 */
public enum FruitMedia {

    APPLE("apple", "http://i.imgur.com/hvFTMrq.jpg"),
    BANANA("banana", "http://www.verangola.net/va/images/cms-image-000001461.jpg"),
    BLUEBERRY("blueberry", "http://i.imgur.com/zFT84iM.jpg"),
    KIWI("kiwi", "http://i.imgur.com/Z0VCR85.jpg"),
    KUMQUAT("kumquat", "http://i.imgur.com/K8Yy1e0.jpg"),
    ORANGE("orange", "http://i.imgur.com/kLaXGgr.jpg"),
    PEAR("pear", "http://i.imgur.com/DHhpmo6.jpg"),
    PITAYA("pitaya", "http://i.imgur.com/kOReBr8.jpg"),
    STRAWBERRY("strawberry", "http://i.imgur.com/3nPvGLr.jpg");


    String url;

    FruitMedia(String name, String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}

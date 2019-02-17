
package com.mti.newviewsv2.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Source {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public static class Builder {

        private String mId;
        private String mName;

        public Source.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Source.Builder withName(String name) {
            mName = name;
            return this;
        }

        public Source build() {
            Source source = new Source();
            source.mId = mId;
            source.mName = mName;
            return source;
        }

    }

}

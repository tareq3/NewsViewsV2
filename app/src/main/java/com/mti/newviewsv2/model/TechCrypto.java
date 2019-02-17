
package com.mti.newviewsv2.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TechCrypto {

    @SerializedName("articles")
    private List<Article> mArticles;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalResults")
    private Long mTotalResults;

    public List<Article> getArticles() {
        return mArticles;
    }

    public String getStatus() {
        return mStatus;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public static class Builder {

        private List<Article> mArticles;
        private String mStatus;
        private Long mTotalResults;

        public TechCrypto.Builder withArticles(List<Article> articles) {
            mArticles = articles;
            return this;
        }

        public TechCrypto.Builder withStatus(String status) {
            mStatus = status;
            return this;
        }

        public TechCrypto.Builder withTotalResults(Long totalResults) {
            mTotalResults = totalResults;
            return this;
        }

        public TechCrypto build() {
            TechCrypto techCrypto = new TechCrypto();
            techCrypto.mArticles = mArticles;
            techCrypto.mStatus = mStatus;
            techCrypto.mTotalResults = mTotalResults;
            return techCrypto;
        }

    }

}

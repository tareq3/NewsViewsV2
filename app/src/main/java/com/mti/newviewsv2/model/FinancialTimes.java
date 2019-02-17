
package com.mti.newviewsv2.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FinancialTimes {

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

        public FinancialTimes.Builder withArticles(List<Article> articles) {
            mArticles = articles;
            return this;
        }

        public FinancialTimes.Builder withStatus(String status) {
            mStatus = status;
            return this;
        }

        public FinancialTimes.Builder withTotalResults(Long totalResults) {
            mTotalResults = totalResults;
            return this;
        }

        public FinancialTimes build() {
            FinancialTimes financialTimes = new FinancialTimes();
            financialTimes.mArticles = mArticles;
            financialTimes.mStatus = mStatus;
            financialTimes.mTotalResults = mTotalResults;
            return financialTimes;
        }

    }

}

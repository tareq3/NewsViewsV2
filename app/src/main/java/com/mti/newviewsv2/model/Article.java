
package com.mti.newviewsv2.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Article {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("publishedAt")
    private String mPublishedAt;
    @SerializedName("source")
    private Source mSource;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("urlToImage")
    private String mUrlToImage;

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public Source getSource() {
        return mSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public static class Builder {

        private String mAuthor;
        private String mContent;
        private String mDescription;
        private String mPublishedAt;
        private Source mSource;
        private String mTitle;
        private String mUrl;
        private String mUrlToImage;

        public Article.Builder withAuthor(String author) {
            mAuthor = author;
            return this;
        }

        public Article.Builder withContent(String content) {
            mContent = content;
            return this;
        }

        public Article.Builder withDescription(String description) {
            mDescription = description;
            return this;
        }

        public Article.Builder withPublishedAt(String publishedAt) {
            mPublishedAt = publishedAt;
            return this;
        }

        public Article.Builder withSource(Source source) {
            mSource = source;
            return this;
        }

        public Article.Builder withTitle(String title) {
            mTitle = title;
            return this;
        }

        public Article.Builder withUrl(String url) {
            mUrl = url;
            return this;
        }

        public Article.Builder withUrlToImage(String urlToImage) {
            mUrlToImage = urlToImage;
            return this;
        }

        public Article build() {
            Article article = new Article();
            article.mAuthor = mAuthor;
            article.mContent = mContent;
            article.mDescription = mDescription;
            article.mPublishedAt = mPublishedAt;
            article.mSource = mSource;
            article.mTitle = mTitle;
            article.mUrl = mUrl;
            article.mUrlToImage = mUrlToImage;
            return article;
        }

    }

}

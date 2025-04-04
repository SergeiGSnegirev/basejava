package com.urise.webapp.model;

public enum ContactType {
    MOBILE_PHONE("Мобильный"),
    EMAIL("Электронная почта"),
    SKYPE("Skype"),
    TELEGRAM("Telegram"),
    LINKEDIN("LinkedIn"),
    GITHUB("Профиль GitHub"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
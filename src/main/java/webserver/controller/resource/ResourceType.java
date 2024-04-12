package webserver.controller.resource;

public enum ResourceType {
    HTML("./templates", "text/html;charset=utf-8"),
    CSS("./static", "text/css"),
    ICO("./templates", "image/x-icon"),
    JS("./static", "application/x-javascript"),
    FONT_WOFF("./static", "font/woff"),
    FONT_TTF("./static", "font/ttf");

    private final String location;
    private final String contentType;

    ResourceType(String location, String contentType) {
        this.location = location;
        this.contentType = contentType;
    }

    public String getLocation() {
        return this.location;
    }

    public String getContentType() {
        return this.contentType;
    }
}

package visualizer;

public enum GraphMode {
    NONE(String.format(getTextFormat(), "None")),
    ADD_VERTEX(String.format(getTextFormat(), "Add a Vertex")),
    ADD_EDGE(String.format(getTextFormat(), "Add an Edge")),
    REMOVE_VERTEX(String.format(getTextFormat(), "Remove a Vertex")),
    REMOVE_EDGE(String.format(getTextFormat(), "Remove an Edge"));

    private static String getTextFormat() {
        return "Current Mode -> %s";
    }

    private final String text;
    GraphMode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

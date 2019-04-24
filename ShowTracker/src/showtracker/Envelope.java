package showtracker;

import java.io.Serializable;

public class Envelope implements Serializable {
    private static final long serialVersionUID = 3158624303211464043L;
    private final Object content;
    private final String type;

    public Envelope(Object content, String type) {
        this.content = content;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }
}
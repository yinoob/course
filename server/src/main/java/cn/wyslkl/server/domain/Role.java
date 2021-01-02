package cn.wyslkl.server.domain;

public class Role {
    private String id;

    private String name;

    private String descIs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescIs() {
        return descIs;
    }

    public void setDescIs(String descIs) {
        this.descIs = descIs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", descIs=").append(descIs);
        sb.append("]");
        return sb.toString();
    }
}
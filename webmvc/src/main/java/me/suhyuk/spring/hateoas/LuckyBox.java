package me.suhyuk.spring.hateoas;

class LuckyBox {
    private String name;
    LuckyBox(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LuckyBox{" +
                "name='" + name + '\'' +
                '}';
    }
}

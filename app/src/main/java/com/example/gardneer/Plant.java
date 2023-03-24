package com.example.gardneer;
import java.util.Comparator;

class Plant
{
    private String id;
    private String name;
    private int image;

    public Plant(String id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static Comparator<Plant> idAscending = new Comparator<Plant>()
    {
        @Override
        public int compare(Plant shape1, Plant shape2)
        {
            int id1 = Integer.valueOf(shape1.getId());
            int id2 = Integer.valueOf(shape2.getId());

            return Integer.compare(id1, id2);
        }
    };

    public static Comparator<Plant> nameAscending = new Comparator<Plant>()
    {
        @Override
        public int compare(Plant shape1, Plant shape2)
        {
            String name1 = shape1.getName();
            String name2 = shape2.getName();
            name1 = name1.toLowerCase();
            name2 = name2.toLowerCase();

            return name1.compareTo(name2);
        }
    };


}

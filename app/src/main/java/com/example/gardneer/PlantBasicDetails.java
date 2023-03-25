package com.example.gardneer;
import java.util.Comparator;

class PlantBasicDetails {
    private String id;
    private String name;
    private int image;
    private String item_type;
    private String zone_type;
    private String season_type;

    public PlantBasicDetails(String id, String name, int image) {
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

    public static Comparator<PlantBasicDetails> idAscending = new Comparator<PlantBasicDetails>()
    {
        @Override
        public int compare(PlantBasicDetails shape1, PlantBasicDetails shape2)
        {
            int id1 = Integer.valueOf(shape1.getId());
            int id2 = Integer.valueOf(shape2.getId());

            return Integer.compare(id1, id2);
        }
    };

    public static Comparator<PlantBasicDetails> nameAscending = new Comparator<PlantBasicDetails>()
    {
        @Override
        public int compare(PlantBasicDetails shape1, PlantBasicDetails shape2)
        {
            String name1 = shape1.getName();
            String name2 = shape2.getName();
            name1 = name1.toLowerCase();
            name2 = name2.toLowerCase();

            return name1.compareTo(name2);
        }
    };


}

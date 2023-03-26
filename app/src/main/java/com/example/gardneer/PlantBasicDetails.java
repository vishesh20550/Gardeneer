package com.example.gardneer;
import java.util.Comparator;

class PlantBasicDetails {
    private int id;
    private String name;
    private int image;

    public PlantBasicDetails(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        public int compare(PlantBasicDetails plant1, PlantBasicDetails plant2)
        {
            int id1 = Integer.valueOf(plant1.getId());
            int id2 = Integer.valueOf(plant2.getId());

            return Integer.compare(id1, id2);
        }
    };

    public static Comparator<PlantBasicDetails> nameAscending = new Comparator<PlantBasicDetails>()
    {
        @Override
        public int compare(PlantBasicDetails plant1, PlantBasicDetails plant2)
        {
            String name1 = plant1.getName();
            String name2 = plant2.getName();
            name1 = name1.toLowerCase();
            name2 = name2.toLowerCase();

            return name1.compareTo(name2);
        }
    };

}

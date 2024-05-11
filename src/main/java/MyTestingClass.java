public class MyTestingClass implements Comparable<MyTestingClass> {
    private String id;

    public MyTestingClass(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(MyTestingClass other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public int hashCode() {
        int h = 17;
        for(int i = 0;i<id.length();i++) {
            h = h* 17 + id.charAt(i);
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyTestingClass other = (MyTestingClass) obj;
        return id.equals(other.id);
    }

    @Override
    public String toString() {
        return id;
    }
}


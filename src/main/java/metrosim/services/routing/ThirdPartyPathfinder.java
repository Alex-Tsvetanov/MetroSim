package metrosim.services.routing;
import java.util.List;
public final class ThirdPartyPathfinder {
    public List<Integer> findPath(int src, int dst){
        java.util.ArrayList<Integer> path=new java.util.ArrayList<>();
        int step = src<=dst?1:-1;
        for (int i=src; i!=dst; i+=step) path.add(i);
        path.add(dst);
        return path;
    }
}

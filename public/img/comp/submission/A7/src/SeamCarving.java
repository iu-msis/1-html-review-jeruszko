import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SeamCarving {
    private int[] pixels;
    private int type, height, width;

    // Field getters

    int[] getPixels() {
        return pixels;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    // Read and write images

    void readImage(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        type = image.getType();
        height = image.getHeight();
        width = image.getWidth();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    void writeImage(String filename) throws IOException {
        BufferedImage image = new BufferedImage(width, height, type);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        ImageIO.write(image, "jpg", new File(filename));
    }

    // Accessing pixels and their neighbors

    Color getColor(int h, int w) {
        int pixel = pixels[w + h * width];
        return new Color(pixel, true);
    }

    // for a general position, returns the four neighbors above,
    // right, below, and left

    ArrayList<Position> getHVneighbors(int h, int w) {
        ArrayList<Position> neighbors = new ArrayList<>();

        if (w == 0) neighbors.add(new Position(h, w + 1));
        else if (w + 1 == width) neighbors.add(new Position(h, w - 1));
        else {
            neighbors.add(new Position(h, w - 1));
            neighbors.add(new Position(h, w + 1));
        }

        if (h == 0) neighbors.add(new Position(h + 1, w));
        else if (h + 1 == height) neighbors.add(new Position(h - 1, w));
        else {
            neighbors.add(new Position(h + 1, w));
            neighbors.add(new Position(h - 1, w));
        }
        
        return neighbors;
    }

    // For a general position, returns the three neighbors
    // under it: below left, below, and below right

    ArrayList<Position> getBelowNeighbors(int h, int w) {
        ArrayList<Position> neighbors = new ArrayList<>();
        if (h + 1 == height) return neighbors;
        neighbors.add(new Position(h + 1, w));
        if (w == 0) {
            neighbors.add(new Position(h + 1, w + 1));
            return neighbors;
        } else if (w + 1 == width) {
            neighbors.add(new Position(h + 1, w - 1));
            return neighbors;
        } else {
            neighbors.add(new Position(h + 1, w + 1));
            neighbors.add(new Position(h + 1, w - 1));
            return neighbors;
        }
    }

    // Computing energy at given pixel
    // Get the 4 surrounding neighbors and sum
    // the squares of the differences of RGB values

    int computeEnergy(int h, int w) {
        Color c = getColor(h, w);
        Function<Integer, Integer> sq = n -> n * n;
        int energy = 0;
        for (Position p : getHVneighbors(h, w)) {
            Color nc = getColor(p.getFirst(), p.getSecond());
            energy += sq.apply(nc.getRed() - c.getRed());
            energy += sq.apply(nc.getGreen() - c.getGreen());
            energy += sq.apply(nc.getBlue() - c.getBlue());
        }
        return energy;
    }

    // Find seam of minimum total energy starting from (h,w) going down
    // returns the list of positions in the seam and its cost
    //
    // use a hashtable to memoize the work
    //
    // The steps to follow are:
    // 1. Compute the energy at the current position
    // 2. Find the neighbors below the current position
    // 3. If there are no neighbors (we are at the bottom row), return the
    //    appropriate result
    // 4. Otherwise, recursively findSeam starting from each neighbor's
    //    position
    // 5. Return the minimum answer after adding the current node and current
    //    energy

    final Map<Position, Pair<List<Position>, Integer>> hash = new HashMap<>();

    Pair<List<Position>, Integer> findSeam(int h, int w) {

        Position key = new Position(h,w);
        if (hash.containsKey(key)) return hash.get(key);

        int en = computeEnergy(h,w);


        ArrayList<Position> nei = getBelowNeighbors(h,w);
        Pair<List<Position>, Integer> p1; //return

        if(nei.isEmpty()) p1 = new Pair(new Node(new Position(h,w), new Empty<>()), en);
        else {
            ArrayList<Position> neighborsofthyneighbors = getBelowNeighbors(h,w);
            Pair<List<Position>, Integer> maxEn = new Pair(new Empty<>(), Integer.MAX_VALUE);

            for (int i = 0; i < neighborsofthyneighbors.size(); i++){
                Pair<List<Position>, Integer> curPair = findSeam(neighborsofthyneighbors.get(i).getFirst(),
                        neighborsofthyneighbors.get(i).getSecond());
                int curEn = curPair.getSecond();
                if (curEn < maxEn.getSecond()) maxEn = curPair;
            }

            int addEn = en + maxEn.getSecond();
            List<Position> l1 = maxEn.getFirst();
            p1 = new Pair(new Node(key, l1), addEn);
         }
        hash.put(key, p1);
        //System.out.println("p1:"+p1);
        return p1;


        //return null; // TODO
    }

    // Call findSeam for all position in the first row (h=0)
    // andd returns the best (the one with the lowest
    // total energy)
    //
    // CLEAR the hashtable before starting; each calculation
    // of bestSeam needs to start with a fresh hashtable
    // but all the calls the findSeam will share the same
    // hashtable

    Pair<List<Position>, Integer> bestSeam() {

        hash.clear();
        Pair<List<Position>, Integer> min;
        Pair<List<Position>, Integer> temp;

        min = findSeam(0,0);
        for (int i = 1; i < this.width; i++){
            temp = findSeam(0,i);
            if (min.getSecond() > temp.getSecond()) min = temp;
        }
        return min; // TODO
    }

    // Putting it all together; find best seam and copy pixels
    // without that seam
    //
    // the logic is to create a small array of pixels, copy all
    // the pixels from the old array to the new array except
    // the ones in the seam

    void cutSeam() {
        try{
            int[] pix = new int[this.height * (this.width-1)];

            Pair<List<Position>, Integer> bs = bestSeam();
            List<Position> cut = bs.getFirst();
            System.out.println("Cut:"+cut);


            for (int r =0; r < this.height-1;r++){
                int i = 0;
                for (int c = 0; c < this.width - 1; c++){

                    if (cut.isEmpty()){
                        pix[i + r * (width-1)] =pixels[r + c * (width)];
                    } else{
                        if (cut.getFirst().getFirst() == r && cut.getFirst().getSecond() == c){
                            i--;
                        }else{
                            pix[r * ((width-1))] = pixels[r  * width];
                        }
                    }
                    i++;
                }
            }
            width = width - 1;
            pixels = pix;
        }catch ( EmptyListE ignored){

        }

      // TODO
    }
}



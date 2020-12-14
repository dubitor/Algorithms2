import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    // create a seam carber object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateColumn(x);
        validateRow(y);
        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1) {
            return 1000; // default value for edge pixels
        }
        return Math.sqrt(deltaXSquared(x, y) + deltaYSquared(x, y));
    }

    private void validateColumn(int col) {
        if (!(0 <= col && col < width())) {
            throw new IllegalArgumentException("invalid column");
        }
    }

    private void validateRow(int row) {
        if (!(0 <= row && row < height())) {
            throw new IllegalArgumentException("invalid row");
        }
    }


    // square of horizontal energy gradient
    private int deltaXSquared(int col, int row) {
        Color left = picture.get(col - 1, row);
        Color right = picture.get(col + 1, row);
        return colourGradSquared(left, right);
    }

    // square of vertical energy gradient
    private int deltaYSquared(int col, int row) {
        Color above = picture.get(col, row - 1);
        Color below = picture.get(col, row + 1);
        return colourGradSquared(above, below);
    }

    // sum of squares of rgb gradients between two colours
    private int colourGradSquared(Color a, Color b) {
        int redDiff = a.getRed() - b.getRed();
        int greenDiff = a.getGreen() - b.getGreen();
        int blueDiff = a.getBlue() - b.getBlue();
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture copy = new Picture(picture);
        transposePicture();
        int[] seam = findVerticalSeam();
        picture = copy;
        return seam;

    }

    private void transposePicture() {
        Picture rotatedPict = new Picture(height(), width());
        for (int col = 0; col < rotatedPict.width(); col++) {
            for (int row = 0; row < rotatedPict.height(); row++) {
                rotatedPict.set(col, row, picture.get(row, col));
            }
        }
        picture = rotatedPict;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] seam = new int[height()]; 

        if (width() == 1) { // corner case
            for (int i = 0; i < seam.length; i++) {
                seam[i] = 0;
            }
            return seam;
        } 

        if (height() == 1) { // corner case
            seam[0] = 0;
            return seam;
        }


        double[][] pathEnergy =  new double[width()][height()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                pathEnergy[col][row] = Integer.MAX_VALUE; // initialise to infitity for other pixels
            }
        }
        for (int col = 0; col < width(); col++) {
            pathEnergy[col][0] = 1000; // initialise path energy of every first row pixel to 1000
        }

        int[][] prevPixel =  new int[width()][height()]; // col index of previous pixel

        for (int row = 1; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                int prev = findMinPredecessor(col, row, pathEnergy);
                double minPrevEnergy = pathEnergy[prev][row - 1];
                pathEnergy[col][row] = energy(col)(row) + minPrevEnergy;
                prevPixel[col][row] = prev;
            }
        }

        int currentRow = height() - 1, currentCol = findLastInPath(pathEnergy);
        while (currentRow >= 0) {
            seam[currentRow] = currentCol;
            currentCol = prevPixel[currentCol][currentRow--];
        }
        assert (seam[1] != 0); // check the array is actually filled: [0][1] == 1000 so shouldn't ever be in the seam
        return seam;

    }

    // column of pixel on bottom row with lowest path energy (for reconstructing seam)
    private int findLastInPath(double[][] pathEnergy) {
        int champion = 0, lastRow = height() - 1;
        for (int col = 0; col < width(); col++) {
            assert (pathEnergy[col][lastRow] != Integer.MAX_VALUE); // pathEnergy must be already filled
            if (pathEnergy[col][lastRow] < pathEnergy[champion][lastRow]) {
                champion = col;
            }
        }
        return champion;
    }

    // column of vertex with minimum path energy out of the possible predecessors
    private int findMinPredecessor(int col, int row, double[][] pathEnergy) {
        assert (row != 0); // shouldn't be called for first row
        if (col == 0) {
            return col + 1; // min pred will never be in end column
        }
        if (col == width() - 1) {
            return col - 1;
        }
        
        int min;
        if (pathEnergy[col - 1][row - 1] < pathEnergy[col][row - 1]
                && pathEnergy[col - 1][row - 1] < pathEnergy[col + 1][row - 1]) {
            min = col - 1;
        }
        else if (pathEnergy[col + 1][row - 1] < pathEnergy[col][row - 1]
                && pathEnergy[col + 1][row - 1] < pathEnergy[col - 1][row - 1]) {
            min = col + 1;
        }
        else {
            min = col;
        }

        return min;

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        transposePicture();
        removeVerticalSeam(seam);
        transposePicture();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateVerticalSeamRemoval(seam);
        
        Picture newPict = new Picture(width() - 1, height());
        for (int row = 0; row < newPict.height(); row++) {
            int offset = 0;
            for (int col = 0; col < newPict.width(); col++) {
                if (col == seam[row]) {
                    offset = 1;
                }
                newPict.set(col, row, picture.get(col + offset, row));
            }
            offset = 0;
        }
        picture = newPict;
    }

    private void validateVerticalSeamRemoval(int[] seam) {
        if (width() <= 1) {
            throw new IllegalArgumentException("picture too small to remove seam");
        }
        if (seam == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException("argument wrong size");
        }
        validateColumn(seam[0]);
        for (int i = 0; i < seam.length - 1; i++) {
            validateColumn(seam[i + 1]);
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("invalid seam");
            }
        }
    }


    // unit testing in auxillary files

}

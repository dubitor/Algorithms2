import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    // create a seam carber object based on the given picture
    public SeamCarver(Picture picture) {
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
        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1) {
            return 1000; // default value for edge pixels
        }
        return Math.sqrt(deltaXSquared(x, y) + deltaYSquared(x, y));
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
        int blueDiff = a.getBlue() - b. getBlue();
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return new int[0];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return new int[0];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
    }

    // unit testing

}

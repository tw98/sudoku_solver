public class VisualizeSuduko {
    public static void main(String[] args) {
        PennDraw.setXscale(0, 100);
        PennDraw.setYscale(0, 100);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setPenRadius(0.005);
        PennDraw.square(50, 55, 40);
        
        double[] xCoord = new double[9];
        double[] yCoord = new double[9];
        
        PennDraw.setPenRadius(0.001);
        // find all xCoordinates
        for( int i = 0; i < 9; i++) {
            double xCoordinate = 10.0 + 80.0/18.0 + i * (160.0/18.0);
            xCoord[i] = xCoordinate;
        }
        
        // find all yCoordinates
        for( int i = 0; i < 9; i++) {
            double yCoordinate = 95 - (80.0/18.0) - i * (160.0/18.0);
            yCoord[i] = yCoordinate;
        }
        
        PennDraw.setFontSize(25);
        //PennDraw.setFontBold();
        
        // draw square of fields
        for ( int i = 0; i < xCoord.length; i++) {
            for ( int j = 0; j < yCoord.length; j++) {
                PennDraw.square( xCoord[i], yCoord[j], 80.0/18.0);
                PennDraw.text( xCoord[i], yCoord[j] - 1.2, "1");
                
                if ( (i-1) % 3 == 0 && (j-1) % 3 == 0) {
                    PennDraw.setPenRadius(0.005);
                    PennDraw.square( xCoord[i], yCoord[j], 3 * (80.0/18.0));
                    PennDraw.setPenRadius(0.001);
                }
            }
        }
    }
}

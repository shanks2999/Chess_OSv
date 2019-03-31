package wrapper;
// POJO created for tracking piece movement
public class PieceTracking_BEAN {
    private String currentLocation;
    private String nextLocation;
    private String piece;
    private String pieceKilled;

    public String getPieceKilled() {
        return pieceKilled;
    }

    public void setPieceKilled(String pieceKilled) {
        this.pieceKilled = pieceKilled;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(String nextLocation) {
        this.nextLocation = nextLocation;
    }
    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }
}

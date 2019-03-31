package wrapper;
// POJO created for keeping track of piece killed
public class GameTracking_BEAN {
    private String status;
    private PieceTracking_BEAN objPieceTracking;
    private String pieceKilled;

    public String getPieceKilled() {
        return pieceKilled;
    }

    public void setPieceKilled(String captured_piece) {
        this.pieceKilled = captured_piece;
    }

    public PieceTracking_BEAN getObjPieceTracking() {
        return objPieceTracking;
    }

    public void setObjPieceTracking(PieceTracking_BEAN objPieceTracking) {
        this.objPieceTracking = objPieceTracking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

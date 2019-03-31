package wrapper;
// POJO created for initial Game object
public class InitializeGame_BEAN {
    private String id;
    private String status;
    private PieceTracking_BEAN objPieceTracking;

    public PieceTracking_BEAN getObjPieceTracking() {
        return objPieceTracking;
    }

    public void setObjPieceTracking(PieceTracking_BEAN moveMade) {
        this.objPieceTracking = moveMade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

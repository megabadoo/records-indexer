package views;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class BatchState {
	
	private String username;
	private String password;
	private int projectId;
	private String imageUrl;
	public transient List<BatchListener> myListeners;
	private int batchId;
	private List<List<String>> values;
	private int currentRow, currentField;
	private double zoomLevel;
	private int scrollPosition;
	private boolean highlightsVisible;
	private boolean imageInverted;
	private int desktopX, deskTopY;
	private int verticalSplit;
	private int horizontalSplit;
	private Point desktopLocation;
	private Dimension desktopSize;
	private Dimension preferredSize;
	
	public BatchState(){
		myListeners = new ArrayList<BatchListener>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public void addToList(BatchListener BL) {
		myListeners.add(BL);
	}


	public void notifyCellChange() {
		for(BatchListener l: myListeners)
			l.cellChanged();
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public List<List<String>> getValues() {
		return values;
	}

	public void setValues(List<List<String>> values2) {
		this.values = values2;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentField() {
		return currentField;
	}

	public void setCurrentField(int currentField) {
		this.currentField = currentField;
	}

	public double getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(double d) {
		this.zoomLevel = d;
	}

	public int getScrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(int scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

	public boolean isHighlightsVisible() {
		return highlightsVisible;
	}

	public void setHighlightsVisible(boolean highlightsVisible) {
		this.highlightsVisible = highlightsVisible;
	}

	public boolean isImageInverted() {
		return imageInverted;
	}

	public void setImageInverted(boolean imageInverted) {
		this.imageInverted = imageInverted;
	}

	public int getDesktopX() {
		return desktopX;
	}

	public void setDesktopX(int desktopX) {
		this.desktopX = desktopX;
	}

	public int getDeskTopY() {
		return deskTopY;
	}

	public void setDeskTopY(int deskTopY) {
		this.deskTopY = deskTopY;
	}

	public Dimension getDesktopSize() {
		return desktopSize;
	}

	public void setDesktopSize(Dimension desktopSize) {
		this.desktopSize = desktopSize;
	}

	public int getVerticalSplit() {
		return verticalSplit;
	}

	public void setVerticalSplit(int split) {
		this.verticalSplit = split;
	}

	public int getHorizontalSplit() {
		return horizontalSplit;
	}

	public void setHorizontalSplit(int horizontalSplit) {
		this.horizontalSplit = horizontalSplit;
	}

	public Point getDesktopLocation() {
		return desktopLocation;
	}

	public void setDesktopLocation(Point desktopLocation) {
		this.desktopLocation = desktopLocation;
	}

	
	
	
}
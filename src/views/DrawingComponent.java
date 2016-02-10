package views;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;

import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent implements BatchListener {

	private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	private int w_translateX;
	private int w_translateY;
	private double scale;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartTranslateX;
	private int w_dragStartTranslateY;
	private AffineTransform dragTransform;

	private ArrayList<DrawingShape> shapes;
	private ArrayList<ArrayList<DrawingShape>> cells;
	private BufferedImage image;
	private Font font;
	private BasicStroke stroke;
	private DrawingComponent drawingComponent;
	
	private BatchState batchState;
	private boolean imageInitialized;
	private IndexingFrame parent;
	private Color highlightColor;
	private Cell selectedCell;
	private boolean toggleHighlights;
	private boolean inverted;
	
	private ArrayList<ArrayList<Cell>> cellInfo;
	
	public DrawingComponent(IndexingFrame p, BatchState bs){
		
		this.parent = p;
		
		this.batchState = bs;
		batchState.addToList(this);
		
		
		drawingComponent = this;
		
		w_translateX = 0;
		w_translateY = 0;
		scale = 1.0;
		
		initDrag();
		selectedCell = null;
		toggleHighlights = true;
		inverted = false;

		shapes = new ArrayList<DrawingShape>();
		cells = new ArrayList<ArrayList<DrawingShape>>();
		cellInfo = new ArrayList<ArrayList<Cell>>();
		
		font = new Font("SansSerif", Font.PLAIN, 72);
		stroke = new BasicStroke(5);
		
		this.setBackground(Color.gray);
		//this.setPreferredSize(new Dimension(700, 700));
		//this.setMinimumSize(new Dimension(100, 100));
		//this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		imageInitialized = false;
		
		highlightColor = new Color(121, 199, 247, 100);
		
//		shapes.add(new DrawingRect(new Rectangle2D.Double(200, 200, 50, 70), new Color(121, 199, 247)));
		
	}
	
	public void setSelectedCell(int record, int field){
		selectedCell = cellInfo.get(record).get(field);
		parent.showFieldHelp(parent.getFieldHelp(field));
	}
	
	public Cell getSelectedCell(){
		return selectedCell;
	}
	
	public void clearImage(){
		if(shapes.size()>0)
			shapes.remove(0);
		cells.clear();
		cellInfo.clear();
		repaint();
	}
	
	public void setImage(String imageUrl){
		
		imageInitialized = true;
		image = loadImage(imageUrl);
		if(shapes.size()>0)
			shapes.remove(0);
		shapes.add(new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null))));
		//shapes.add(new DrawingRect(new Rectangle2D.Double(200, 200, 70, 30), new Color(121, 199, 247, 100)));
		repaint();
	}
	
	public boolean getInverted(){
		return inverted;
	}
	
	public void setToggleHighlights(boolean highlights){
		toggleHighlights = highlights;
	}
	
	public void setInverted(boolean inverted){
		this.inverted = inverted;
	}
	
	public void drawCell(int x, int y, int width, int height, int record, int field){

		if(record==cells.size()){
			ArrayList<DrawingShape> recordCell = new ArrayList<DrawingShape>();
			ArrayList<Cell> recordCellInfo = new ArrayList<Cell>();
			if(cells.size()==0){
				recordCell.add(new DrawingRect(new Rectangle2D.Double(x/2, y/2, width/2, height/2), new Color(121, 199, 247, 50)));
				recordCellInfo.add(new Cell(x, y, width, height, record, field));
				selectedCell = recordCellInfo.get(0);
				
			}
			else{
				recordCell.add(new DrawingRect(new Rectangle2D.Double(x/2, y/2, width/2, height/2), new Color(121, 199, 247, 0)));
				recordCellInfo.add(new Cell(x, y, width, height, record, field));
			}
			cells.add(recordCell);
			
			cellInfo.add(recordCellInfo);
			if(cells.size()>0)
				setSelectedCell(0,0);
		}
		else{
			cells.get(record).add(new DrawingRect(new Rectangle2D.Double(x/2, y/2, width/2, height/2), new Color(121, 199, 247, 0)));
			cellInfo.get(record).add(new Cell(x, y, width, height, record, field));
		}
		//shapes.add(new DrawingRect(new Rectangle2D.Double(x/2, y/2, width/2, height/2), new Color(121, 199, 247, 0)));
		repaint();
	}
	
	public void highlightCell(int record, int field){
		
		int x = cellInfo.get(record).get(field).getX();
		int y = cellInfo.get(record).get(field).getY();
		int width = cellInfo.get(record).get(field).getWidth();
		int height = cellInfo.get(record).get(field).getHeight();
		cells.get(record).add(field, new DrawingRect(new Rectangle2D.Double(x/2, y/2, width/2, height/2), new Color(121, 199, 247, 50)));
		cells.get(record).remove(field+1);
		cells.get(selectedCell.getRecord()).add(selectedCell.getField(), new DrawingRect(new Rectangle2D.Double(selectedCell.getX()/2, selectedCell.getY()/2, selectedCell.getWidth()/2, selectedCell.getHeight()/2), new Color(121, 199, 247, 0)));
		cells.get(selectedCell.getRecord()).remove(selectedCell.getField()+1);
		selectedCell = cellInfo.get(record).get(field);
		
		repaint();
	}
	
	public void toggleHighlights(){

		if(toggleHighlights){
			if(cells.size()>0){
				cells.get(selectedCell.getRecord()).add(selectedCell.getField(), new DrawingRect(new Rectangle2D.Double(selectedCell.getX()/2, selectedCell.getY()/2, selectedCell.getWidth()/2, selectedCell.getHeight()/2), new Color(121, 199, 247, 0)));
				cells.get(selectedCell.getRecord()).remove(selectedCell.getField()+1);
			}
		
		}
		else{
			if(cells.size()>0){
				cells.get(selectedCell.getRecord()).add(selectedCell.getField(), new DrawingRect(new Rectangle2D.Double(selectedCell.getX()/2, selectedCell.getY()/2, selectedCell.getWidth()/2, selectedCell.getHeight()/2), new Color(121, 199, 247, 50)));
				cells.get(selectedCell.getRecord()).remove(selectedCell.getField()+1);
			}
		
		}
		
		toggleHighlights = !toggleHighlights;
		
		repaint();
	}
	
	
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartTranslateX = 0;
		w_dragStartTranslateY = 0;
		dragTransform = null;
	}
	
	private BufferedImage loadImage(String imageUrl) {
		BufferedImage batch = null;
		try {
			Image toolkitImage = ImageIO.read(new URL(imageUrl));
			toolkitImage = toolkitImage.getScaledInstance(toolkitImage.getWidth(null) / 2, toolkitImage.getHeight(null) / 2, toolkitImage.SCALE_DEFAULT);
			batch = new BufferedImage(toolkitImage.getWidth(null), toolkitImage.getHeight(null), 
				      BufferedImage.TYPE_INT_ARGB);
				Graphics g = batch.getGraphics();
				g.drawImage(toolkitImage, 0, 0, null);
				g.dispose();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		image = batch;
		return batch;
	}
	
	public void setScale(double newScale) {
		scale = newScale;
		
		this.repaint();
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setTranslation(int w_newTranslateX, int w_newTranslateY) {
		w_translateX = w_newTranslateX;
		w_translateY = w_newTranslateY;
		this.repaint();
	}
	
	public boolean getToggleHighlights(){
		return toggleHighlights;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);

//		if(imageInitialized)
		g2.translate(drawingComponent.getWidth( )/2.0, drawingComponent.getHeight( )/2.0);
		g2.scale(scale, scale);
		if(image!=null)
			g2.translate( -image.getWidth(null)/2 + w_translateX, -image.getHeight(null)/2.0 + w_translateY);
//		g2.translate(5, 5);

		drawShapes(g2);
		drawCells(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	private void drawCells(Graphics2D g2) {
		for(int x=0; x<cells.size(); x++){
			for (DrawingShape cell : cells.get(x)) {
				cell.draw(g2);
			}
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
//			transform.scale(scale, scale);
//			transform.translate(w_translateX, w_translateY);
			
			transform.translate(drawingComponent.getWidth( )/2.0, drawingComponent.getHeight( )/2.0);
			transform.scale(scale, scale);
			if(image!=null)
				transform.translate( -image.getWidth(null)/2 + w_translateX, -image.getHeight(null)/2.0 + w_translateY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			boolean hitCell = false;
			for(int i=0; i<cellInfo.size(); i++){
				for (int j=0; j<cellInfo.get(i).size(); j++) {
					if (cells.get(i).get(j).contains(g2, w_X, w_Y)) {
						hitCell = true;
						if(toggleHighlights)
							highlightCell(i,j);
						setSelectedCell(i,j);
						if(batchState.getCurrentRow()!=i || batchState.getCurrentField()!=j){
							batchState.setCurrentRow(i);
							batchState.setCurrentField(j);
							batchState.notifyCellChange();
						}
						break;
					}
				}
				if(hitCell)
					break;
			}
			hitCell = false;
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartTranslateX = w_translateX;
				w_dragStartTranslateY = w_translateY;
				dragTransform = transform;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					dragTransform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_translateX = w_dragStartTranslateX + w_deltaX;
				w_translateY = w_dragStartTranslateY + w_deltaY;
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			return;
		}	
	};
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			return;
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};

	
	/////////////////
	// Drawing Shape
	/////////////////
	
	
	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}


	class DrawingRect implements DrawingShape {

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	class DrawingLine implements DrawingShape {

		private Line2D line;
		private Color color;
		
		public DrawingLine(Line2D rect, Color color) {
			this.line = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {

			final double TOLERANCE = 5.0;
			
			Point2D p1 = line.getP1();
			Point2D p2 = line.getP2();
			Point2D p3 = new Point2D.Double(x, y);
			
			double numerator = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p3.getY() - p1.getY()) * (p2.getY() - p1.getY());
			double denominator =  p2.distance(p1) * p2.distance(p1);
			double u = numerator / denominator;
			
			if (u >= 0.0 && u <= 1.0) {
				Point2D pIntersection = new Point2D.Double(	p1.getX() + u * (p2.getX() - p1.getX()),
															p1.getY() + u * (p2.getY() - p1.getY()));
				
				double distance = pIntersection.distance(p3);
				
				return (distance <= TOLERANCE);
			}
			
			return false;
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.setStroke(stroke);
			g2.draw(line);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return line.getBounds2D();
		}
	}


	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	class DrawingText implements DrawingShape {

		private String text;
		private Color color;
		private Point2D location;
		
		public DrawingText(String text, Color color, Point2D location) {
			this.text = text;
			this.color = color;
			this.location = location;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			Rectangle2D bounds = getBounds(g2);
			return bounds.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.setFont(font);
			g2.drawString(text, (int)location.getX(), (int)location.getY());
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(text, context);
			bounds.setRect(location.getX(), location.getY() + bounds.getY(), 
							bounds.getWidth(), bounds.getHeight());
			return bounds;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String value) {
			text = value;
		}
	}
	
	public void invertImage () {
		inverted = !inverted;
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		BufferedImage negative = op.filter(image, null);
		shapes.add(0, new DrawingImage(negative, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null))));
		shapes.remove(1);
		repaint();
	}


	@Override
	public void cellChanged() {
		int newRow = batchState.getCurrentRow();
		int newCol = batchState.getCurrentField();
		int oldRow = getSelectedCell().getRecord();
		int oldCol = getSelectedCell().getField();
		if(oldRow!=newRow || oldCol != newCol) {
			
			if(toggleHighlights)
				highlightCell(newRow, newCol);
			setSelectedCell(newRow, newCol);
		}
		
	}
	
}




package design.objects;
import design.objects.MyLabel.LabelType.BackgroundColor;
import design.objects.MyLabel.LabelType.BackgroundHoverColor;
import design.objects.MyLabel.LabelType.Type;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import referring.css.ReferringCss;
public class MyLabel extends Label {

	private Type type;
	private BackgroundColor backColor;
	private BackgroundHoverColor hoverColor;
	private int size;
	private ReferringCss cssReferrer;
	public MyLabel(String text, int size, Type type, BackgroundColor color, BackgroundHoverColor hover) {
		this.cssReferrer = new ReferringCss();
		this.cssReferrer.referringLabel(this);	
		
		this.size = size;
		this.type = type;
		this.backColor = color;
		this.hoverColor = hover;
		this.setText(text);
		defineLabelStyle();
	}
	public void defineLabelStyle() {
		this.setFont(new Font(getSize()));
		/*
		 * define icon
		 */
		if (LabelType.Type.EMAIL_ICON.equals(type)) { this.getStyleClass().add("EMAIL-ICON");}
		if (LabelType.Type.FRIEND_ICON.equals(type)) {this.getStyleClass().add("FRIEND_ICON-ICON");	}
		if (LabelType.Type.MESSAGE_ICON.equals(type)) {	this.getStyleClass().add("MESSAGE_ICON-ICON");}
		if (LabelType.Type.PROJECT_INVITE_ICON.equals(type)) {this.getStyleClass().add("EMAIL-ICON");}
		if (LabelType.Type.PROJECT_INVITE_ICON.equals(type)) {this.getStyleClass().add("PROJECT_INVITE_ICON-ICON");}
		if (LabelType.Type.NO_ICON.equals(type)) {this.getStyleClass().add("NO_ICON-ICON");}
		if(LabelType.Type.TITLE.equals(type)) {this.getStyleClass().add("TITLE");
		}
		/*
		 * define backgroundcolor
		 */
		if (getBackColor().equals(LabelType.BackgroundColor.GREY)) {this.getStyleClass().add("GREY");}
		if (getBackColor().equals(LabelType.BackgroundColor.DARK_GREY)) {this.getStyleClass().add("DARK_GREY");}
		if (getBackColor().equals(LabelType.BackgroundColor.WHITE)) {this.getStyleClass().add("WHITE");}
		if (getBackColor().equals(LabelType.BackgroundColor.BLACK)) {this.getStyleClass().add("BLACK");	}
		if (getBackColor().equals(LabelType.BackgroundColor.GREEN)) {this.getStyleClass().add("GREEN");}
		if (getBackColor().equals(LabelType.BackgroundColor.DARK_GREEN)) {this.getStyleClass().add("DARK_GREEN");}
		/*
		 * define hover color
		 */
		if (getHoverColor().equals(LabelType.BackgroundHoverColor.GREY_HOVER)) {this.getStyleClass().add("GREY_HOVER");}
		if (getHoverColor().equals(LabelType.BackgroundHoverColor.DARK_GREY_HOVER)) {this.getStyleClass().add("DARK_GREY_HOVER");}
		if (getHoverColor().equals(LabelType.BackgroundHoverColor.WHITE_HOVER)) {this.getStyleClass().add("WHITE_HOVER");}
		if (getHoverColor().equals(LabelType.BackgroundHoverColor.BLACK_HOVER)) {this.getStyleClass().add("BLACK_HOVER");}
		if (getHoverColor().equals(LabelType.BackgroundHoverColor.GREEN_HOVER)) {this.getStyleClass().add("GREEN_HOVER");}
		if (getHoverColor().equals(LabelType.BackgroundHoverColor.DARK_GREEN_HOVER)) {this.getStyleClass().add("DARK_GREEN_HOVER");}
	
	}
	public int getSize() {return this.size;}

	public Type getType() {return type;}
	public void setType(Type type) {this.type = type;}
	
	public BackgroundColor getBackColor() {return backColor;}
	public void setBackColor(BackgroundColor backColor) {this.backColor = backColor;}

	public BackgroundHoverColor getHoverColor() {return hoverColor;}
	public void setHoverColor(BackgroundHoverColor hoverColor) {this.hoverColor = hoverColor;}

	
	public static class LabelType {
		public static enum Type {
			TITLE, NO_ICON, EMAIL_ICON, FRIEND_ICON, MESSAGE_ICON, PROJECT_INVITE_ICON, SEARCH_BUTTON_ICON
		};
		public static enum BackgroundColor {
			GREY, DARK_GREY, WHITE, BLACK, GREEN, DARK_GREEN;
		};
		public static enum BackgroundHoverColor {
			GREY_HOVER, DARK_GREY_HOVER, WHITE_HOVER, BLACK_HOVER, GREEN_HOVER, DARK_GREEN_HOVER;
		}
	}
}
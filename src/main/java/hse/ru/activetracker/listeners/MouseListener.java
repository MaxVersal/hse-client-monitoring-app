package hse.ru.activetracker.listeners;

import hse.ru.activetracker.models.MouseEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MouseListener implements NativeMouseInputListener, Listener<MouseEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MouseListener.class);
    private Long mouseClicks;
    private Long mousePresses;
    private Double mouseDistance;
    private Integer xAxisPrevious;
    private Integer yAxisPrevious;

    public MouseListener() {
        this.mouseClicks = 0L;
        this.mousePresses = 0L;
        this.mouseDistance = 0.0;
        this.xAxisPrevious = 0;
        this.yAxisPrevious = 0;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        LOGGER.debug("Mouse Clicked: {}", e.getButton());
        mouseClicks++;
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        LOGGER.debug("Mouse Pressed: {}", e.getButton());
        mousePresses++;
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        LOGGER.debug("Mouse Released: {}", e.getButton());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        System.out.println("move detected");
        calculateDistance(e.getX(), e.getY());
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        LOGGER.debug("Mouse dragged is not implemented");
    }

    private void calculateDistance(int x, int y) {
        System.out.println("calculating distance ");
        mouseDistance += Math.sqrt(Math.pow(x - xAxisPrevious, 2) + Math.pow(y - yAxisPrevious, 2));
        xAxisPrevious = x;
        yAxisPrevious = y;
    }

    public MouseEvent collectData() {
        return new MouseEvent(mouseClicks, mouseDistance, mousePresses);
    }

    public void clear() {
        this.mouseClicks = 0L;
        this.mousePresses = 0L;
        this.mouseDistance = 0.0;
        this.xAxisPrevious = 0;
        this.yAxisPrevious = 0;
    }
}

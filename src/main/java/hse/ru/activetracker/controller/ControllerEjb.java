package hse.ru.activetracker.controller;

import hse.ru.activetracker.listeners.KeyboardListener;
import hse.ru.activetracker.listeners.MouseListener;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.concurrent.*;

public class ControllerEjb {
    private static final int MAX_THREADS_COUNT = 1;
    private final String user;
    private final KeyboardListener keyListener;
    private final MouseListener mouseListener;
    private ScheduledExecutorService scheduleReader;
    private ThreadFactory managedThreadFactory;
    private ReaderTask readerTask;

    public ControllerEjb(String user) {
        this.user = user;
        this.keyListener = new KeyboardListener();
        this.mouseListener = new MouseListener();
        this.managedThreadFactory = new BasicThreadFactory.Builder().build();
        this.scheduleReader = Executors.newScheduledThreadPool(MAX_THREADS_COUNT, managedThreadFactory);
    }

    public void init() throws NativeHookException {
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeKeyListener(keyListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);
        GlobalScreen.registerNativeHook();
        this.readerTask = new ReaderTask(mouseListener, keyListener);
    }

    public void send(String project) {
        readerTask.send(project);
    }
}

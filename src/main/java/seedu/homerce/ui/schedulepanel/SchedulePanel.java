package seedu.homerce.ui.schedulepanel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.homerce.commons.core.LogsCenter;
import seedu.homerce.model.appointment.Appointment;
import seedu.homerce.model.service.Duration;
import seedu.homerce.ui.UiPart;

/**
 * Panel containing list of Appointments.
 */
public class SchedulePanel extends UiPart<Region> {
    public static final String TAB_NAME = "Schedule";
    private static final int SINGLE_COLUMN_WIDTH = 120;
    private static final int ROW_SPAN = 1;
    private static final int NUM_OF_HALF_HOURS = 48;
    private static final double SCALE_FACTOR = 2.0;
    private static final int GRID_INDEX_BUFFER = 1;
    private static final String FXML = "schedulepanel/SchedulePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(seedu.homerce.ui.schedulepanel.SchedulePanel.class);

    private final ObservableList<Appointment> appointments;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public SchedulePanel(ObservableList<Appointment> appointments) {
        super(FXML);
        this.appointments = appointments;
    }

    /**
     * Fills the grid pane with the slots.
     */
    public void construct() {
        if (appointments.size() == 0) {
            System.out.println("No appointments in schedule");
            return;
        }
        constructGrid();
        addAppointmentSlotsToGrid();
        addDateDisplaySlotsToGrid();
    }

    private int getColSpan(Duration duration) {
        // Service timings are in 0.5 hours so multiplication by an even SCALE_FACTOR will always give an a whole number
        return (int) (duration.value * SCALE_FACTOR);
    }

    private int getColIndex(LocalTime startTime) {
        int colIndex = startTime.getHour() * (int) SCALE_FACTOR;
        if (startTime.getMinute() != 0) {
            // Account for 0.5h in the schedule
            colIndex += 1;
        }
        // Account for 0 index
        return colIndex - 1;
    }

    /**
     * Returns the column index that fits into the grid after changing the start index to the earliest timing
     * and taking into account the slot taken up by the date display
     */
    private int getAdjustedColIndex(Appointment appointment) {
        int currIndex = getColIndex(appointment.getAppointmentStartTime().getLocalTime());
        return currIndex - getColIndex(getEarliestStartTime()) + GRID_INDEX_BUFFER;
    }

    private boolean isSameDate(LocalDate currDate, LocalDate next) {
        // Dates are sorted so next date will only be after or the same as currDate
        assert !next.isBefore(currDate) : "Dates not sorted properly";
        if (next.isAfter(currDate)) {
            return false;
        }
        return true;
    }

    private LocalTime getEarliestStartTime() {
        return appointments.stream()
            .map(appointment -> appointment.getAppointmentStartTime().getLocalTime())
            .reduce((time1, time2) -> (time1.isBefore(time2) ? time1 : time2))
            .get();
    }

    private LocalTime getLatestEndTime() {
        return appointments.stream()
            .map(appointment -> appointment.getAppointmentEndTime().getLocalTime())
            .reduce((time1, time2) -> (time1.isAfter(time2) ? time1 : time2))
            .get();

    }

    private List<LocalDate> getListOfUniqueDates() {
        return appointments.stream().map(x -> x.getAppointmentDate()
            .getLocalDate()).distinct().collect(Collectors.toList());
    }

    private void constructGrid() {
        int earliestTimeIndex = getColIndex(getEarliestStartTime());
        int latestTimeIndex = getColIndex(getLatestEndTime());

        int numColumns = NUM_OF_HALF_HOURS - earliestTimeIndex - (NUM_OF_HALF_HOURS - latestTimeIndex);
        for (int i = 0; i < numColumns + GRID_INDEX_BUFFER; i++) {
            ColumnConstraints con = new ColumnConstraints();
            con.setPrefWidth(SINGLE_COLUMN_WIDTH);
            gridPane.getColumnConstraints().add(con);
        }
    }

    private void addAppointmentSlotsToGrid() {
        int rowIndex = 0;
        LocalDate prevAppointmentDate = appointments.get(0).getAppointmentDate().getLocalDate();

        for (int i = 0; i < appointments.size(); i++) {
            Appointment curr = appointments.get(i);
            LocalDate currAppointmentDate = curr.getAppointmentDate().getLocalDate();
            SlotContainer appointmentSlot;
            if (curr.getService().getAmount().value.doubleValue() <= 30) {
                appointmentSlot = new AppointmentSlotRed(curr);
            } else if (curr.getService().getAmount().value.doubleValue() <= 60) {
                appointmentSlot = new AppointmentSlotBlue(curr);
            } else {
                appointmentSlot = new AppointmentSlotGreen(curr);
            }
            if (!isSameDate(prevAppointmentDate, currAppointmentDate)) {
                rowIndex++;
                prevAppointmentDate = currAppointmentDate;
            }
            int colIndex = getAdjustedColIndex(curr);
            int colSpan = getColSpan(curr.getService().getDuration());
            gridPane.add(appointmentSlot.getRoot(), colIndex, rowIndex, colSpan, ROW_SPAN);
        }
    }

    private void addDateDisplaySlotsToGrid() {
        List<LocalDate> dates = getListOfUniqueDates();
        for (int i = 0; i < dates.size(); i++) {
            DisplayDateSlot slot = new DisplayDateSlot(dates.get(i));
            gridPane.add(slot.getRoot(), 0, i, 1, ROW_SPAN);
        }
    }
}

package edu.wpi.cs3733.D22.teamC.controller.map.data.service_request;

import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Locale;

/**
 * A single Service Request Token, representing a Service Request of a given type.
 */
public class ServiceRequestToken {
    // FXML
    @FXML private Group root;
    @FXML private Label iconLabel;

    // Variables
    ServiceRequest.RequestType requestType;

    // References
    private ServiceRequestNode parentNode;

    public void setup(ServiceRequestNode serviceRequestNode) {
        this.parentNode = serviceRequestNode;
    }

    public void setPosition(int x, int y) {
        root.setTranslateX(x);
        root.setTranslateY(y);
    }

    public void setType(ServiceRequest.RequestType requestType) {
        this.requestType = requestType;
        updateIcon();
    }

    /**
     * Updates icon based on location node type.
     */
    public void updateIcon(){
        SVGParser svgParser = new SVGParser();
        String content = svgParser.getPath("static/icons/" + requestType.toString().toUpperCase(Locale.ROOT) + ".svg");

        SVGGlyph glyph = new SVGGlyph(content);
        glyph.getStyleClass().add(requestType.toString().toLowerCase(Locale.ROOT) + "-icon");
        iconLabel.setGraphic(glyph);
    }

    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        setVisible(serviceRequests.size() > 0);
    }

    public void setVisible(boolean visible) {
        root.setVisible(visible);
    }
}

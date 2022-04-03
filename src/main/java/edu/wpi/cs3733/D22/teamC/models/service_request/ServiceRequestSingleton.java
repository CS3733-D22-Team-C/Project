package edu.wpi.cs3733.D22.teamC.models.service_request;

public class ServiceRequestSingleton {
    private ServiceRequestTable serverRequestTable;
    public static ServiceRequestSingleton INSTANCE = new ServiceRequestSingleton();

    private ServiceRequestSingleton() {}

    public void setServerRequestTable(ServiceRequestTable u) {
        this.serverRequestTable = u;
    }

    public ServiceRequestTable getServiceRequestTable() {
        return this.serverRequestTable;
    }
}

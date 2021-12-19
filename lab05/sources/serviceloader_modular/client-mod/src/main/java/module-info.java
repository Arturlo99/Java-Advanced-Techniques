import ex.api.ClusterAnalysisService;

module client_mod {
	exports serviceloader.client;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens serviceloader.client;
    
    uses ClusterAnalysisService;
	requires api_mod;
}
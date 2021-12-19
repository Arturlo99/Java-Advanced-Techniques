import ex.api.ClusterAnalysisService;
import implementation.Example;

module impl01_mod {
	exports implementation;

	requires api_mod;
	provides ClusterAnalysisService
	with Example;
}
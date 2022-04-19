package tools;

import cartago.*;

public class RoomArtifact extends Artifact {

  void init(){
    defineObsProperty("roomBright", false);
    }

  @OPERATION void increaseLight(String agent, String alarmType){
    log("Increase Light operation called | Agent: " + agent + " | Alarm type: " + alarmType);
    ObsProperty prop = getObsProperty("roomBright");
    log("Room Bright status is " + prop.booleanValue());
    if(prop.booleanValue() == true){
      failed("Room already bright");
    }
    switch (alarmType) {
      case "artificial_light":
        // Handle turning on of lamp
        break;
      case "natural_light":
        // Handle raising of blinds
        break;
      default:
        break;
    }
    prop.updateValue(true);
    signal("roomBright", prop.booleanValue(), agent, alarmType);
  }
}

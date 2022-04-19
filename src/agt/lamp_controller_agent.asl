/* Maria's lamp controller agent */

/*
  The agent believes that it can offer regulating the artificial light
  for the task of waking up Maria
*/
available_offer(wake_up(maria), artificial_light).


/* Initial goals */
!start.


/* Behavior for handling goal-achievement addition start */
@start
+!start : true
<-
  .print("Hello from lamp controller agent.").


/* TASK 3 - STEP 1
  Behavior for handling the belief addition cfp(Task)
  Implement the behavior for responding to Call For Proposals
  by sending a Propose with an artificial_light offer.
*/
+cfp(Task)[source(Agent)] :
  available_offer(Task, Offer)
<-
  .print("Received CALL FOR PROPOSAL for task ", Task, " from ", Agent, " offer ", Offer);
  .send(Agent, tell, offer(Offer)).

+accept_proposal(AlarmType)[source(Agent)] : true <-
  .print("Accept Proposal message received from ", Agent, " for alarm type ", AlarmType);
  makeArtifact("roomArtifact", "tools.RoomArtifact", [], RoomArtifact);
  focus(RoomArtifact);
  increaseLight(Agent, AlarmType).

+roomBright(Boolean, Agent, AlarmType)[artifact_name(Id, RoomArtifact)]: 
AlarmType == "artificial_light" &
available_offer(Task, artificial_light) <-
  .print("Room brightness status has been changed to ", Boolean, " from Alarm Type: ", AlarmType);
  .print("Informing agent ", Agent, " of updated status for task ", Task);
  .send(Agent, tell, informDone(Task)).

/* Additional behaviors */
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
{ include("$jacamoJar/templates/org-obedient.asl") }

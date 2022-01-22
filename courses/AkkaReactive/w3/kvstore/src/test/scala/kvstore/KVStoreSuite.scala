package kvstore

import akka.actor.ActorSystem

class KVStoreSuite
  extends munit.FunSuite
    with Step1_PrimarySpec
    with Step2_SecondarySpec
    with Step3_ReplicatorSpec
    with Step4_SecondaryPersistenceSpec
    with Step5_PrimaryPersistenceSpec
    with Step6_NewSecondarySpec
    with IntegrationSpec
    with Tools:

  given system: ActorSystem = ActorSystem("KVStoreSuite")


import { Formik } from "formik"
import React, { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import styles from "./CreateProposalForm.module.scss"
import { Box, Step, StepLabel, Stepper, Typography } from "@mui/material"
import CountryTable from "../../CountryManagement/CountryTable/CountryTable"
import Button from "../../../components/Button/Button"
import {
  getProposalById,
  setProposalSubscriber,
  startProposal,
} from "../../../services/ProposalService"
import SubscriberTable from "../../SubscriberManagement/SubscriberTable/SubscriberTable"
import PolicyService from "../../../services/PolicyService"
import { ToastService } from "../../../services/ToastService"
import { Proposal } from "../../../redux/types/Proposal"
import {
  ProposalStatus,
  ProposalStatusToInt,
} from "../../../redux/enums/ProposalStatus"
import SubscriberModal from "../SubscriberModal/SubscriberModal"

type Props = {}

const steps = [
  "Select subscriber",
  "Select car",
  "Select drivers",
  "Pricing",
  "Confirmation",
  "Payment",
]

function CreateProposalForm({}: Props) {
  const [activeStep, setActiveStep] = useState(0)
  const [skipped, setSkipped] = useState(new Set<number>())
  const [selectedSubscriberId, setSelectedSubscriberId] = useState<number>()
  const { proposalId } = useParams()
  const [proposal, setProposal] = useState<Proposal>()
  const [isSubscriberModalOpen, setIsSubscriberModalOpen] = useState(false)

  useEffect(() => {
    getProposalById(Number.parseInt(proposalId!)).then(result => {
      setProposal(result)
      if (result.subscriberId) {
        setSelectedSubscriberId(result.subscriberId)
      }
      setActiveStep(ProposalStatusToInt[result.proposalStatus])
    })
  }, [])

  const stepComponents: any[] = [
    <SubscriberTable
      selectable={true}
      selectedRow={selectedSubscriberId}
      onSelect={(id: number) => {
        setSelectedSubscriberId(id)
        setIsSubscriberModalOpen(true)
      }}
    />,
    "Car select", // <CarSearch/>,
    "Driver select", // <DriverSearch/>,
    "Pricing", // <Pricing/>
    "Confirmation", // <Confirmation/>
    "Payment", // <Payment/>
  ]

  const isStepOptional = (step: number) => {
    return step === -1
  }

  const isStepSkipped = (step: number) => {
    return skipped.has(step)
  }

  const handleNext = () => {
    let newSkipped = skipped
    if (isStepSkipped(activeStep)) {
      newSkipped = new Set(newSkipped.values())
      newSkipped.delete(activeStep)
    }

    switch (activeStep) {
      case 0: {
        if (proposalId) {
          setProposalSubscriber(
            Number.parseInt(proposalId),
            selectedSubscriberId!,
          )
        } else {
          ToastService.notifyError("Proposal id not found")
        }
      }
    }

    setActiveStep(prevActiveStep => prevActiveStep + 1)
    setSkipped(newSkipped)
  }

  const handleBack = () => {
    setActiveStep(prevActiveStep => prevActiveStep - 1)
  }

  const handleSkip = () => {
    if (!isStepOptional(activeStep)) {
      throw new Error("You can't skip a step that isn't optional.")
    }

    setActiveStep(prevActiveStep => prevActiveStep + 1)
    setSkipped(prevSkipped => {
      const newSkipped = new Set(prevSkipped.values())
      newSkipped.add(activeStep)
      return newSkipped
    })
  }

  const handleReset = () => {
    setActiveStep(0)
  }

  return (
    <div className="main-wrapper">
      {!proposal ? (
        <h1 className="heading">Proposal doesn't exist.</h1>
      ) : (
        <>
          {proposal?.proposalStatus == "CONFIRMED" ? (
            <h1 className="heading">Proposal already confirmed.</h1>
          ) : (
            <>
              <h1 className="heading">Create Proposal</h1>
              <Stepper activeStep={activeStep} className={styles.stepper}>
                {steps.map((label, index) => {
                  const stepProps: { completed?: boolean } = {}
                  const labelProps: {
                    optional?: React.ReactNode
                  } = {}
                  if (isStepOptional(index)) {
                    labelProps.optional = (
                      <Typography variant="caption">Optional</Typography>
                    )
                  }
                  if (isStepSkipped(index)) {
                    stepProps.completed = false
                  }
                  return (
                    <Step key={label} {...stepProps}>
                      <StepLabel {...labelProps}>
                        <span className={styles.step}>{label}</span>
                      </StepLabel>
                    </Step>
                  )
                })}
              </Stepper>
              {activeStep === steps.length ? (
                <React.Fragment>
                  <Typography sx={{ mt: 2, mb: 1 }}>
                    All steps completed - you&apos;re finished
                  </Typography>
                  <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
                    <Box sx={{ flex: "1 1 auto" }} />
                    <Button color="accent" onClick={handleReset}>
                      Reset
                    </Button>
                  </Box>
                </React.Fragment>
              ) : (
                <React.Fragment>
                  {stepComponents[activeStep]}
                  <div className={styles["stepper-buttons"]}>
                    <Button
                      color="accent"
                      onClick={handleBack}
                      className={styles.btn}
                      disabled={activeStep === 0 || activeStep === 5}
                    >
                      Back
                    </Button>
                    <Box sx={{ flex: "1 1 auto" }} />
                    {isStepOptional(activeStep) && (
                      <Button
                        color="inherit"
                        onClick={handleSkip}
                        className={styles.btn}
                      >
                        Skip
                      </Button>
                    )}
                    <Button
                      onClick={handleNext}
                      color="primary"
                      className={styles.btn}
                      disabled={activeStep == 0 && !selectedSubscriberId}
                    >
                      {activeStep === steps.length - 1 ? "Finish" : "Next"}
                    </Button>
                  </div>
                </React.Fragment>
              )}
            </>
          )}
        </>
      )}
      <SubscriberModal
        isOpen={isSubscriberModalOpen}
        subscriberId={selectedSubscriberId!}
        onSelect={() => {
          setIsSubscriberModalOpen(false)
        }}
        onClose={() => {
          setSelectedSubscriberId(
            proposal!.subscriberId ? proposal!.subscriberId : -1,
          )
          setIsSubscriberModalOpen(false)
        }}
      />
    </div>
  )
}

export default CreateProposalForm

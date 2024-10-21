import CardFlex from "../../../components/CardFlex/CardFlex"
import { Proposal } from "../../../redux/types/Proposal"
import ProposalCard from "../ProposalCard/ProposalCard"

type ProposalProps = {
  proposals: Array<Proposal>
}

const ProposalFlex = (props: ProposalProps) => {
  return (
    <CardFlex data-testid="proposal-card-flex">
      {props.proposals.map((proposal: Proposal) => (
        <ProposalCard proposal={proposal} key={proposal.id} />
      ))}
    </CardFlex>
  )
}

export default ProposalFlex

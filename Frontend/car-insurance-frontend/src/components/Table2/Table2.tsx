import React from "react"
import styles from "./Table2.module.scss"

type TableProps = {
	headings: string[],
	rows: any[],
	'data-testid'?: string
}

const Table2 = ({ 'data-testid': dataTestId, headings, rows }: TableProps) => {
  return (
    <div className={styles.wrapper}>
		  <table className={styles.table} data-testid={dataTestId}>
		  <thead>
			<tr>
			  {headings.map((heading: string, index: number) => (
				<th key={index} scope="col" className={styles.heading}>
				  {heading}
				</th>
			  ))}
			</tr>
		  </thead>
		  <tbody>
			{rows.map((row: any[], rowIndex: number) => (
				<tr key={`row-${rowIndex}`}>
				{row.map((cell: any, cellIndex: number) => {
					const key = `cell-${rowIndex}-${cellIndex}`;
					return (
					<td key={key} className={styles.content}>
						{cell}
					</td>
					);
				})}
				</tr>
			))}
		</tbody>
		</table>
	</div>
  )
}

export default Table2

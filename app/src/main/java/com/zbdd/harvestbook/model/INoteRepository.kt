package com.zbdd.harvestbook.model

/**
 * Interface for INoteRepository calls that implements the default Repository interface but casts
 * our generic to the expected INote interface
 *
 * @author Zac Durber
 */
interface INoteRepository: IBaseRepository<INote> {

}
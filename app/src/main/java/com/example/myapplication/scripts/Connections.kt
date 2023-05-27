package com.example.myapplication.scripts

// function for connections between blocks
fun connectBlocks(blockFrom: Block, blockTo: Block, clear: Boolean = true) {
    // stopping infinite loop
    if (blockFrom == blockTo) return

    // clearing connections if needed
    if (clear) {
        blockFrom.nextBlock?.prevBlock = null
        blockTo.prevBlock?.nextBlock = null
    }
    blockFrom.nextBlock = blockTo
    blockTo.prevBlock = blockFrom
}
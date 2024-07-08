Keeping a **virtual account** is great way to forego cumbersome cash transfers or handing over physical money. Instead, you **keep track** how much money you have advanced for someone else until, finally, you **settle** the **account** after *many* transactions.

This app provides for such *virtual account*. It also adds some further funtions for convenience.

*Note that the only purpose of this app is to keep track of transactions without having to settle the account after each of them. No measures of establishing trust (e.g., proofs) are included. Hence, only use the app when trust is pre-established.*

# Use Cases

## Settle Account

| ![settle-account-0](gallery/settle-account-0.png) | ![settle-account-1](gallery/settle-account-1.png) | ![settle-account-2](gallery/settle-account-2.png) |
| :--: | :--: | :--: |
| Start the settlement | Confirm <br> *or click on no to cancel* | Balance reset to zero <br> all transactions removed |

## Book Transaction by Applying Template

| ![book-template-0](gallery/book-template-0.png) | ![book-template-1](gallery/book-template-1.png) |
| :--: | :--: |
| Book transaction according to template `SELF-PAID LUNCH` | Transaction booked <br> *updates: the balance and the list of transactions* |

## Book Custom Transaction

| ![book-custom-0](gallery/book-custom-0.png) | ![book-custom-1](gallery/book-custom-1.png) | ![book-custom-2](gallery/book-custom-2.png) | ![book-custom-3](gallery/book-custom-3.png) |
| :--: | :--: | :--: | :--: |
| Start custom transaction | Enter amount and, optionally, description | Trigger the booking | Transaction booked |

## Book Custom Transaction and Retain as Template

| ![book-custom-and-store-template-0](gallery/book-custom-and-store-template-0.png) | ![book-custom-and-store-template-1](gallery/book-custom-and-store-template-1.png) | ![book-custom-and-store-template-2](gallery/book-custom-and-store-template-2.png) | ![book-custom-and-store-template-3](gallery/book-custom-and-store-template-3.png) |
| :--: | :--: | :--: | :--: |
| Start custom transaction | Check the `Save as template` box <br> after having entered amount and description | Trigger the booking | Transaction booked and template created |

## Undo Transaction

| ![remove-transaction-0](gallery/remove-transaction-0.png) | ![remove-transaction-1](gallery/remove-transaction-1.png) | ![remove-transaction-2](gallery/remove-transaction-2.png) | ![remove-transaction-3](gallery/remove-transaction-3.png) |
| :--: | :--: | :--: | :--: |
| Navigate to the transaction overview | Select the transaction | Swipe it to the left | Transaction undone <br> *also updates the balance* |

## Discard Template

| ![remove-template-0](gallery/remove-template-0.png) | ![remove-template-1](gallery/remove-template-1.png) | ![remove-template-2](gallery/remove-template-2.png) |
| :--: | :--: | :--: |
| Long-press on template | Confirm the deletion <br> *or click outside to cancel* | Template (and its button) removed |

# Trivia

* *Visual design:* Heavily leans on the [Commodore 64](https://en.wikipedia.org/wiki/Commodore_64) with its fixed palette and fixed-width font. Still, some liberties have been taken:
	* The font is rounded, instead of being based on a `8x8` pixel array.
	* Small characters are averted as, back in the days, that meant that "drawing" characters were not available any more. However, they are still used occasionally (e.g., hints).
	* Visual elements are not placed within the block matrix, like the `40x25` matrix of the [Standard Character Mode](https://www.c64-wiki.com/wiki/Standard_Character_Mode).
	* Dark green has been added as one further color (the 17th) for consistent coloring within the `VIEW ALL ACTIVITIES` screen.
* *No permissions needed:* As seldom as this might be in 2024, the app only needs the `SharedPreferences` (and nothing else).
	* ![app-info](gallery/app-info.png)


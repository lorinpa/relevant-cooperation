
export const ROW_DEFS = {
    NO_ROW_ACTIVE: -1
}


export class RowController {

        active_row: number;

        constructor() {
          this.active_row = ROW_DEFS.NO_ROW_ACTIVE;
        }

        public setActiveRow(id:number) :void {
            if (this.active_row === ROW_DEFS.NO_ROW_ACTIVE) {
              this.active_row = id;
            }
        }

        public getActiveRow(): number {
          return this.active_row;
        }

        resetActive() :void {
          this.active_row = ROW_DEFS.NO_ROW_ACTIVE;
        }

}
